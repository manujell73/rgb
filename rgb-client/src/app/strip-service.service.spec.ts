import { TestBed } from '@angular/core/testing';

import { StripServiceService } from './strip-service.service';

describe('StripServiceService', () => {
  let service: StripServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StripServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
