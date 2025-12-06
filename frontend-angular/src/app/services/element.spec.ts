import { TestBed } from '@angular/core/testing';

import { ElementService } from './element.service';

describe('Element', () => {
  let service: ElementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
