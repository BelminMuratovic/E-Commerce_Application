import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CommerceService } from '../../shared/commerce.service';
import { ProductRequest, ProductResponse } from '../../shared/commerce.model';
import { ToastrService } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product-create-popup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './product-create-popup.component.html',
  styleUrl: './product-create-popup.component.scss',
})
export class ProductCreatePopupComponent {
  data = {
    productType: '',
    productName: '',
    productQuantity: 0,
    productPrice: 0,
  };

  selectedFile!: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  imageName: any;

  constructor(
    public dialogRef: MatDialogRef<ProductCreatePopupComponent>,
    private commerceService: CommerceService,
    private toastr: ToastrService,
    private httpClient: HttpClient
  ) {}

  public onFileChanged(event: any) {
    this.selectedFile = event.target.files[0];
  }

  saveProduct() {
    const newProduct = new ProductRequest(
      this.data.productType,
      this.data.productName,
      this.data.productQuantity,
      this.data.productPrice
    );

    this.commerceService
      .createProduct(newProduct)
      .subscribe((data: ProductResponse) => {
        if (data) {
          this.toastr.success('Product created!');
          this.dialogRef.close();
        } else {
          this.toastr.error('Product creation failed!');
        }

        const uploadImageData = new FormData();
        uploadImageData.append(
          'imageFile',
          this.selectedFile,
          this.selectedFile.name
        );

        this.commerceService
          .uploadImage(uploadImageData, data.id)
          .subscribe((response) => {
            if (response.status === 200) {
              console.log('Image uploaded successfully');
            } else {
              console.log('Image not uploaded successfully');
            }
          });
      });
  }

  cancel() {
    this.dialogRef.close();
  }
}
