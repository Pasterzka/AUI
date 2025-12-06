import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './category-list.html',
  styleUrl: './category-list.css',
})
export class CategoryList implements OnInit {
  categories$: Observable<Category[]> | undefined;

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categories$ = this.categoryService.getCategories();
    console.log('Dane pobrane:', this.categories$);
  }


  deleteCategory(id: string): void {
    if (confirm('Usunąć?')) {
      this.categoryService.deleteCategory(id).subscribe(() => {
        this.categories$ = this.categoryService.getCategories();
      });
    }

  }
}
