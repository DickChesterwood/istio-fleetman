import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PopUpComponent } from './pop-up.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [PopUpComponent],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
  ],
  exports: [PopUpComponent]
})
export class PopUpModule { }
