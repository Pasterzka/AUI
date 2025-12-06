import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElementForm } from './element-form';

describe('ElementForm', () => {
  let component: ElementForm;
  let fixture: ComponentFixture<ElementForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElementForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElementForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
