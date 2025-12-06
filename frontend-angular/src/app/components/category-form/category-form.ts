import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './category-form.html',
  styleUrl: './category-form.css',
})
export class CategoryForm implements OnInit{

  category = {
    name: '',
    country: ''
  };

  categoryId: string | null = null;

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Sprawdzamy, czy w URL jest ID (jeśli tak, to edycja, jeśli nie - dodawanie)
    this.categoryId = this.route.snapshot.paramMap.get('id');

    if (this.categoryId) {
      // Jeśli edycja - pobierz istniejące dane
      this.categoryService.getCategory(this.categoryId).subscribe(data => {
        this.category = { name: data.name, country: data.country || '' };
      });
    }
  }

  onSubmit(): void {
    if (this.categoryId) {
      // Edycja (PUT)
      this.categoryService.updateCategory(this.categoryId, this.category).subscribe(() => {
        this.router.navigate(['/categories']);
      });
    } else {
      // Dodawanie (POST)
      this.categoryService.createCategory(this.category).subscribe(() => {
        this.router.navigate(['/categories']);
      });
    }
  }
}
