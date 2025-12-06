import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { ElementService } from '../../services/element.service';
import { Category } from '../../models/category';
import { Element } from '../../models/element';

@Component({
  selector: 'app-category-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './category-details.html',
  styleUrls: ['./category-details.css']
})
export class CategoryDetails implements OnInit{
  category: Category | undefined;
  elements: Element[] = [];
  categoryId: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    private elementService: ElementService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Pobierz ID ligi z adresu URL (np. /categories/123)
    this.categoryId = this.route.snapshot.paramMap.get('id') || '';

    if (this.categoryId) {
      this.loadData();
    }
  }

  loadData(): void {
    // Pobierz szczegóły Ligi
    this.categoryService.getCategory(this.categoryId).subscribe(data => {
      this.category = data;
      this.cdr.detectChanges(); // Odśwież widok
    });

    // Pobierz Drużyny należące do tej ligi
    this.elementService.getElementsByCategory(this.categoryId).subscribe(data => {
      this.elements = data;
      this.cdr.detectChanges(); // Odśwież widok
    });
  }

  // Usuwanie pojedynczej drużyny z listy
  deleteElement(element: Element): void {
    if (confirm(`Usunąć drużynę ${element.name}?`)) {
      this.elementService.deleteElement(this.categoryId, element.id).subscribe(() => {
        this.loadData(); // Po usunięciu odśwież tabelę
      });
    }
  }

  // Usuwanie całej ligi (wraz z drużynami - kaskada backendu)
  deleteCategory(): void {
    if (confirm('Usunąć całą ligę?')) {
      this.categoryService.deleteCategory(this.categoryId).subscribe(() => {
        this.router.navigate(['/categories']); // Wróć do listy głównej
      });
    }
  }

}
