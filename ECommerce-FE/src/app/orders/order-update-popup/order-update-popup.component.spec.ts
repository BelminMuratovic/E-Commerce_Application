import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderUpdatePopupComponent } from './order-update-popup.component';

describe('OrderUpdatePopupComponent', () => {
  let component: OrderUpdatePopupComponent;
  let fixture: ComponentFixture<OrderUpdatePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderUpdatePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OrderUpdatePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
