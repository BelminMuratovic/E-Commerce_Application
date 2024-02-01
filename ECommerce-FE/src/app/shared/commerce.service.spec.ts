import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { CommerceService } from './commerce.service';

describe('CommerceService', () => {
  let service: CommerceService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(CommerceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
