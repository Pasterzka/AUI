import { Routes } from '@angular/router';
import { CategoryList } from './components/category-list/category-list';
import { CategoryForm } from './components/category-form/category-form';
import { CategoryDetails } from './components/category-details/category-details';
import { ElementForm } from './components/element-form/element-form';
import { ElementDetails } from './components/element-details/element-details';

export const routes: Routes = [
  // --- KATEGORIE ---
  { path: 'categories', component: CategoryList },
  { path: 'categories/new', component: CategoryForm },
  { path: 'categories/:id/edit', component: CategoryForm },
  { path: 'categories/:id', component: CategoryDetails },

  // --- ELEMENTY (DRUŻYNY) ---

  // 1. Dodawanie
  { path: 'categories/:leagueId/elements/new', component: ElementForm },

  // 2. Edycja
  { path: 'categories/:leagueId/elements/:elementId/edit', component: ElementForm },

  // 3. Szczegóły
  { path: 'categories/:leagueId/elements/:elementId', component: ElementDetails },

  { path: '', redirectTo: '/categories', pathMatch: 'full' }
];
