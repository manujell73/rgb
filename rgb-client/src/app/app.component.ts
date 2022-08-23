import { Component } from '@angular/core';
import { interval } from 'rxjs';
import { Pattern } from './pattern';
import { Strip } from './strip';
import { StripServiceService } from './strip-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string = 'rgb-client';
  strip!: Strip;
  color: string = '#FF0000';
  patterns: Pattern[] = [];
  selectedPattern!: number;

  constructor(private stripService: StripServiceService) {
    interval(33).subscribe(() => this.resetStrip());
  }

  ngOnInit() {
    this.resetStrip();
    this.stripService.getPatterns().subscribe(res => {
      this.patterns = res;
      this.selectedPattern = 0;
    });
  }

  resetStrip() {
    this.stripService.getStripInfo().subscribe(result => this.strip = result);
  }

  submitPattern() {
    this.stripService.submitPattern(this.patterns[this.selectedPattern]).subscribe(result => this.resetStrip());
    
  }

  submitColor() {
    this.stripService.setSingleColor(parseInt(this.color.substring(1), 16)).subscribe(result => this.resetStrip());
    
  }
}
