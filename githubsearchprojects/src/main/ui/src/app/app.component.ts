import { Component, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AppService } from './app.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {

  constructor(private appService: AppService) {}

  title = 'angular-nodejs-example';

  githubForm = new FormGroup({
    username: new FormControl('', Validators.nullValidator && Validators.required),
    token: new FormControl('', Validators.nullValidator && Validators.required),
    searchstring: new FormControl('', Validators.nullValidator && Validators.required)
  });

  projects: any[] = [];
  searchCount = 0;

  destroy$: Subject<boolean> = new Subject<boolean>();

  searchProjects() {
    this.appService.searchProjects(this.githubForm.value).pipe(takeUntil(this.destroy$)).subscribe((projects: any[]) => {
		this.searchCount = projects.length;
        this.projects = projects;
    });
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit() {
	this.searchProjects();
  }
}
