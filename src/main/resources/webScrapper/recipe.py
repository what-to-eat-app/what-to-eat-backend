import base64
import json
import os

from bs4 import BeautifulSoup
import requests

from database import select, insert
from PIL import Image


def convert_to_binary_data(filename):
    # Convert digital data to binary format
    if filename:
        with open(filename, 'rb') as file:
            blob_data = base64.b64encode(file.read())
        return blob_data
    return None


class Recipe:

    def __init__(self, url):
        self.url = url

    recipes_dico = {}

    def put_recipe_in_dico(self, recipe, headers):
        if recipe.h3 and recipe.h3.a:
            recipe_category = recipe.find('div', class_='category').text if recipe.find('div', class_='category') \
                else 'Misc'
            recipe_name = recipe.h3.text
            print('recipe name:', recipe_name)
            recipe_url = recipe.h3.a['href']
            recipe_image_url = recipe.find('div', class_='col img-block').a.span.span['data-srcset'] \
                if recipe.find('div', class_='col img-block') else None
            recipe_image_name = None
            if recipe_image_url:
                recipe_image_name = recipe_image_url.split('/')[-1]
                r = requests.get(recipe_image_url, headers=headers)
                with open(recipe_image_name, 'wb') as outfile:
                    outfile.write(r.content)
                recipe_image = Image.open(recipe_image_name).convert('RGB')
                recipe_image.save(recipe_image_name, 'jpeg')

            # setting-up database for insert
            select_recipe = "SELECT * FROM recipe WHERE name = ? OR url = ?;"
            select_values = (recipe_name, recipe_url[:-1])
            recipes = select(select_recipe, select_values)
            if not recipes and recipe_category != 'Menus & Collections':
                insert_recipe = "INSERT INTO recipe (name, url, category, image) VALUES (?, ?, ?, ?);"
                converted_recipe_image = convert_to_binary_data(recipe_image_name)
                insert_values = (recipe_name, recipe_url[:-1],
                                 recipe_category, converted_recipe_image)
                insert(insert_recipe, insert_values)
            elif recipes and recipe_category != 'Menus & Collections':
                insert_recipe = "UPDATE recipe set name=? , url=?, category=?, image=? where name=?;"
                converted_recipe_image = convert_to_binary_data(recipe_image_name)
                insert_values = (recipe_name, recipe_url[:-1],
                                 recipe_category, converted_recipe_image, recipe_name)
                insert(insert_recipe, insert_values)
            if recipe_image_name:
                os.remove(recipe_image_name)

    def get_recipes_from_url(self):
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
                          'Chrome/56.0.2924.76 Safari/537.36'}  # This is chrome, you can set whatever browser you like

        num = 1
        url = self.url.format(num)
        status_code = requests.get(url, headers=headers).status_code

        i = 1
        while status_code == 200:
            print('page number:', num)
            print('url:', url)
            html_text = requests.get(url, headers=headers).text

            soup = BeautifulSoup(html_text, 'lxml')
            recipes_html_tags = soup.find_all('div', class_='post-item')

            if len(recipes_html_tags) == 1 and recipes_html_tags[0].p.text == 'Recipes not found.':
                break
            else:
                for recipe in recipes_html_tags:
                    self.put_recipe_in_dico(recipe, headers)
                    i += 1
            num += 1
            url = 'https://www.forksoverknives.com/recipes/page/{}/?type=grid'.format(num)
            status_code = requests.get(url, headers=headers).status_code

        try:
            os.remove("recipes.json")
        except OSError:
            pass
        file_name = (url.split('www.'))[1].split('.com')[0] + '-recipes.json'
        with open(file_name, 'w') as convert_file:
            convert_file.write(json.dumps(self.recipes_dico))
        return file_name

    @staticmethod
    def get_all_recipes():
        select_recipe = "SELECT * FROM recipe;"
        recipes = select(select_recipe, [])
        return recipes
