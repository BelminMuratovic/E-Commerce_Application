import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderPreviewPopupComponent } from './order-preview-popup.component';

describe('OrderPreviewPopupComponent', () => {
  let component: OrderPreviewPopupComponent;
  let fixture: ComponentFixture<OrderPreviewPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderPreviewPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OrderPreviewPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
