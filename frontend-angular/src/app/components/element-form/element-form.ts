import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ElementService } from '../../services/element.service';

@Component({
  selector: 'app-element-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './element-form.html',
  styleUrls: ['./element-form.css']
})
export class ElementForm implements OnInit {
  leagueId: string = '';
  elementId: string | null = null;
  element = { name: '', city: '', rating: 50 };

  constructor(
    private elementService: ElementService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 1. Pobieramy ID ligi (Musi się nazywać 'leagueId' tak jak w app.routes.ts)
    this.leagueId = this.route.snapshot.paramMap.get('leagueId') || '';
    this.elementId = this.route.snapshot.paramMap.get('elementId');

    console.log('League ID:', this.leagueId); // <--- Sprawdź w konsoli (F12) czy to nie jest puste!

    if (this.elementId) {
      this.elementService.getElement(this.leagueId, this.elementId).subscribe(data => {
        this.element = { name: data.name, city: data.city, rating: data.rating };
      });
    }
  }

  onSubmit(): void {
    if(!this.leagueId) {
      alert('Błąd: Brak ID ligi!');
      return;
    }

    if (this.elementId) {
      this.elementService.updateElement(this.leagueId, this.elementId, this.element).subscribe(() => this.goBack());
    } else {
      this.elementService.createElement(this.leagueId, this.element).subscribe(() => this.goBack());
    }
  }

  goBack(): void {
    this.router.navigate(['/categories', this.leagueId]);
  }
}
