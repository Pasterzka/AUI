import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElementDetails } from './element-details';

describe('ElementDetails', () => {
  let component: ElementDetails;
  let fixture: ComponentFixture<ElementDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElementDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElementDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
