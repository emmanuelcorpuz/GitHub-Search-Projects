import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-display-board',
  templateUrl: './display-board.component.html',
  styleUrls: ['./display-board.component.css']
})
export class DisplayBoardComponent implements OnInit {

  constructor() { }

  @Input() searchCount = 0;
  @Output() searchProjectsEvent = new EventEmitter();

  ngOnInit(): void {
  }

  searchProjects() {
    this.searchProjectsEvent.emit('search projects');
  }

}
