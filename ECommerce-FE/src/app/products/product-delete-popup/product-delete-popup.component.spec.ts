import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductDeletePopupComponent } from './product-delete-popup.component';

describe('ProductDeletePopupComponent', () => {
  let component: ProductDeletePopupComponent;
  let fixture: ComponentFixture<ProductDeletePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductDeletePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProductDeletePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
