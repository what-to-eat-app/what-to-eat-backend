import json

from bs4 import BeautifulSoup
import requests

import database


def get_instruction(i, instruction_name, url):
    instruction = instruction_name.text

    save_instructions(database, instruction, url, i)


def save_instructions( database, instruction_name, url, i):
    # To reference the recipe id, we need to find the recipe using its url
    select_recipe_query = "SELECT * FROM recipe WHERE url = ?;"
    select_values = [url]
    recipe_id = database.select(select_recipe_query, select_values)[0][0] \
        if database.select(select_recipe_query, select_values) else None
    # Check the instruction has not been inserted before
    # we then save data if not
    if recipe_id:
        insert_recipe = "INSERT OR IGNORE INTO instruction (instruction_description, recipeid, pos) VALUES (?, ?, ?)"
        insert_values = (instruction_name, recipe_id, i)
        database.insert(insert_recipe, insert_values)


class Instruction:

    def __init__(self, url: str):
        self.url = url

    recipe_dico = {}

    def get_recipe_instructions(self):
        dico, subsection_instructions = self.define_constants()
        if subsection_instructions:
            for instructions in subsection_instructions:

                instruction = instructions.find_all('li')
                i = 0
                for instruction_name in instruction:
                    get_instruction(i, instruction_name, self.url)

                    i += 1
                    self.recipe_dico['instructions'] = dico

    def define_constants(self):
        global subsection_instructions
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
                          'Chrome/56.0.2924.76 Safari/537.36'}  # This is chrome, you can set whatever browser you like
        html_page = requests.get(self.url, headers=headers).text
        soup = BeautifulSoup(html_page, 'lxml')
        recipe_instructions = soup.find('section', class_='section-instruction')
        if recipe_instructions:
            subsection_instructions = recipe_instructions.find_all('ol')

        dico = {}
        return dico, subsection_instructions

# To test
# instruction = Instruction('https://www.forksoverknives.com/recipes/vegan-desserts/no-bake-cranberry-pear-tart')
# instruction.get_recipe_instructions()
