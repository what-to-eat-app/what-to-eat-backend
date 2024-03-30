import { Component, Input, OnInit } from '@angular/core';
import { RecipeService } from '../_services/recipe.service';
import { Recipe } from '../recipes/recipe';
import { ActivatedRoute } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import * as _ from 'lodash';
import { Ingredient } from '../ingredients/ingredient';
import { Instruction } from '../recipes/instruction';


@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss']
})


export class RecipeDetailComponent implements OnInit {

  id!: number;
  recipe!: Recipe;
  initialRecipe!: Recipe;

  imagePath!: SafeResourceUrl;
  newImagePath!: SafeResourceUrl;

  editMode!: boolean;

  loading = true;

  constructor(private recipeService: RecipeService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private formBuilder: FormBuilder) { }


  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id']
    });

    this.recipeService.getRecipe(this.id).subscribe((data) => {
      this.recipe = data;
      this.initialRecipe = _.cloneDeep(this.recipe);
      this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.recipe.image);
      this.loading = false;
    });
  }

  edit(): void {
    this.editMode = true;
  }

  save(): void {
    this.editMode = false;
    this.recipeService.saveRecipe(this.recipe.id, this.recipe).subscribe((data) => {
      this.recipe = data;
      this.initialRecipe = _.cloneDeep(this.recipe);
      this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.recipe.image);
      this.loading = false;
      console.log(this.recipe);
    });
  }
  cancel(): void {
    this.recipe = this.initialRecipe;
    this.editMode = false;
  }

  onIngredientNameInput(event: any, index: number) {
    if (event.target) {
      const newValue = event.target.textContent?.trim(); // Use textContent
      this.recipe.ingredients[index].name = newValue || ''; // Update model property
      event.target.textContent = newValue || ''; // Update span content
    }
  }
  
  onIngredientMeasureInput(event: any, index: number) {
    if (event.target) {
      const newValue = event.target.textContent?.trim(); // Use textContent
      this.recipe.ingredients[index].measure = newValue || ''; // Update model property
      event.target.textContent = newValue || ''; // Update span content
    }
  }

  onInstDescrChanged(event : any, i: number) {
    if (event.target) {
      this.recipe.instructions[i].description;
      this.recipe.instructions[i].description = event.target.textContent?.trim();//the ! removes null or undefined 
    }
  }

  uploadImage(event : any) {
    this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl(URL.createObjectURL(event.target.files[0]));
    const reader = new FileReader();
    reader.onloadend = () => {
      const base64String = reader.result as string;
      console.log(base64String.replace('data:image/jpg;base64,',''));
      this.recipe.image = base64String.split(',')[1];
    };
    if (event.target.files[0]) {
      reader.readAsDataURL(event.target.files[0]);
      
    }
    console.log(this.imagePath);
  }


  dataURItoBlob(dataURI : string) {
    // convert base64 to raw binary data held in a string
    var byteString = atob(dataURI.split(',')[1]);

    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

    // write the bytes of the string to an ArrayBuffer
    var arrayBuffer = new ArrayBuffer(byteString.length);
    var _ia = new Uint8Array(arrayBuffer);
    for (var i = 0; i < byteString.length; i++) {
        _ia[i] = byteString.charCodeAt(i);
    }

    var dataView = new DataView(arrayBuffer);
    var blob = new Blob([dataView], { type: mimeString });
    return blob;
}

}
