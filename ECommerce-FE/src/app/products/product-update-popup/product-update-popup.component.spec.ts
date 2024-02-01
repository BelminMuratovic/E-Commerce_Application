import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductUpdatePopupComponent } from './product-update-popup.component';

describe('ProductUpdatePopupComponent', () => {
  let component: ProductUpdatePopupComponent;
  let fixture: ComponentFixture<ProductUpdatePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductUpdatePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProductUpdatePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
