import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderDeletePopupComponent } from './order-delete-popup.component';

describe('OrderDeletePopupComponent', () => {
  let component: OrderDeletePopupComponent;
  let fixture: ComponentFixture<OrderDeletePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderDeletePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OrderDeletePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
