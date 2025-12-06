import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // <--- DODANO ChangeDetectorRef
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ElementService } from '../../services/element.service';
import { Element } from '../../models/element';

@Component({
  selector: 'app-element-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './element-details.html',
  styleUrls: ['./element-details.css']
})
export class ElementDetails implements OnInit {
  element: Element | undefined;
  leagueId: string = '';
  elementId: string = '';

  constructor(
    private elementService: ElementService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.leagueId = this.route.snapshot.paramMap.get('leagueId') || '';
    this.elementId = this.route.snapshot.paramMap.get('elementId') || '';

    if (this.elementId) {
      this.loadData();
    }
  }

  loadData(): void {
    this.elementService.getElement(this.leagueId, this.elementId).subscribe({
      next: (data) => {
        console.log('Otrzymano dane elementu:', data); // Log dla pewności
        this.element = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Błąd pobierania:', err)
    });
  }

  deleteThisElement(): void {
    if (confirm('Czy na pewno chcesz usunąć tę drużynę?')) {
      this.elementService.deleteElement(this.leagueId, this.elementId).subscribe(() => {
        this.router.navigate(['/categories', this.leagueId]);
      });
    }
  }
}
