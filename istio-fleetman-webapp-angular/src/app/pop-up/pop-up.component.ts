import { Component, Input, OnInit } from '@angular/core';
import { openCloseAnimation, openCloseShadeAnimation } from './animations';

@Component({
  selector: 'app-pop-up',
  templateUrl: './pop-up.component.html',
  styleUrls: ['./pop-up.component.scss'],
  animations: [
    openCloseAnimation,
    openCloseShadeAnimation,
  ]
})
export class PopUpComponent implements OnInit {

  @Input() isOpen = false;

  constructor() { }

  ngOnInit() {
  }
}
