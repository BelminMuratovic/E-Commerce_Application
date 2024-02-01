import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCreatePopupComponent } from './product-create-popup.component';

describe('ProductPopupComponent', () => {
  let component: ProductCreatePopupComponent;
  let fixture: ComponentFixture<ProductCreatePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductCreatePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProductCreatePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
