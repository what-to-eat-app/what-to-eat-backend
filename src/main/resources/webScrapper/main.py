from recipe import Recipe

from ingredient import Ingredient
from instruction import Instruction
from tqdm import tqdm

recipe = Recipe('https://www.forksoverknives.com/recipes/page/1/?type=grid')
#recipes = recipe.get_recipes_from_url()
recipes = recipe.get_all_recipes()
if recipes:
    for recipe in tqdm(recipes):
        ingredient = Ingredient(str(recipe[2]))
        ingredient.get_recipe_ingredients()
        instruction = Instruction(str(recipe[2]))
        instruction.get_recipe_instructions()


