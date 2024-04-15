import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecipesComponent } from './recipes/recipes.component';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component';
import { HomeComponent } from './home/home.component';
import { IngredientsComponent } from './ingredients/ingredients.component';
import { RelatedRecipesComponent } from './ingredients/related-recipes/related-recipes.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './_helpers/auth.guard';

const routes: Routes = [
  {path:'', redirectTo:'/home', pathMatch: 'full'},
  {path:'login', component: LoginComponent},
  {path:'home', component:HomeComponent, canActivate:[authGuard], data:{roles: ['ROLE_ADMIN','ROLE_USER']}},
  {path:'recipes', component: RecipesComponent},
  {path:'recipes/detail/:id', component: RecipeDetailComponent},
  {path:'ingredients', component: IngredientsComponent},
  {path:'ingredients/:name/recipes', component: RelatedRecipesComponent},
  {path:'**', component:PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
