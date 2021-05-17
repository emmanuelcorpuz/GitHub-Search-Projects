import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class AppService {

  private REST_API_SERVER = "http://localhost:8080/webfont/project/";
  constructor(private http: HttpClient) {  }

  searchProjects(github: any) {
	console.log(this.REST_API_SERVER + github.searchstring + '?username=' + github.username + '&token=' + github.token);
    return this.http.get(this.REST_API_SERVER + github.searchstring + '?username=' + github.username + '&token=' + github.token);
  }


}
