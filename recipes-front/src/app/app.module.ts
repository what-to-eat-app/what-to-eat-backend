import { NgModule, forwardRef } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecipesComponent } from './recipes/recipes.component';

import { HttpClientModule } from '@angular/common/http';
import { IngredientsComponent } from './ingredients/ingredients.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component';
import { HomeComponent } from './home/home.component';
import { PanelModule } from 'primeng/panel';
import { RelatedRecipesComponent } from './ingredients/related-recipes/related-recipes.component';
import {MatCardModule} from '@angular/material/card';

import {MatButtonModule} from '@angular/material/button';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { ImageModule } from 'primeng/image';
import { NgsContenteditableModule } from '@ng-stack/contenteditable';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    RecipesComponent,
    IngredientsComponent,
    RecipeDetailComponent,
    HomeComponent,
    RelatedRecipesComponent,
    PageNotFoundComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    TableModule,
    ButtonModule,
    BrowserAnimationsModule,
    PanelModule,
    MatCardModule,
    MatButtonModule,
    InputTextareaModule,
    FormsModule,
    ReactiveFormsModule,
    ImageModule,
    NgsContenteditableModule
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RecipeDetailComponent,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
