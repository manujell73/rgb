import { Component } from '@angular/core';
import { interval } from 'rxjs';
import { PatternList } from './patternList';
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
  patterns: PatternList = {};
  decorators: PatternList = {};
  selectedPattern: string|null = null;
  selectedDecorator: string|null = null;

  constructor(private stripService: StripServiceService) {
    interval(33).subscribe(() => this.resetStrip());
  }

  ngOnInit() {
    let temp:string|null;
    temp=localStorage.getItem('patternCache');
    if(temp != null){
      this.patterns = JSON.parse(temp);
    }
    else{
      this.stripService.getPatterns().subscribe(res => {
        this.patterns = res;
        this.selectedPattern = '';
      });
    }
    temp=localStorage.getItem('decoratorCache');
    if(temp != null){
      this.decorators = JSON.parse(temp);
    }
    else{
      this.resetStrip();
      this.stripService.getDecorators().subscribe(res => {
        this.decorators = res;
        this.selectedDecorator = '';
      });
    } 
    this.selectedPattern=localStorage.getItem('selectedPattern');
    this.selectedDecorator=localStorage.getItem('selectedDecorator');
  }

  resetStrip() {
    this.stripService.getStripInfo().subscribe(result => this.strip = result);
  }

  submitPattern() {
    if(this.selectedPattern == null) return;
    this.stripService.submitPattern(this.patterns, this.selectedPattern).subscribe(result => this.resetStrip());
  }

  submitDecorator() {
    if(this.selectedDecorator == null) return;
    this.stripService.submitDecorator(this.decorators, this.selectedDecorator).subscribe(result => this.resetStrip());
  }

  submitColor() {
    this.stripService.setSingleColor(parseInt(this.color.substring(1), 16)).subscribe(result => this.resetStrip());
  }
  
  parameterChanged() {
    localStorage.setItem('decoratorCache', JSON.stringify(this.decorators));
    localStorage.setItem('patternCache', JSON.stringify(this.patterns));
    if(this.selectedPattern != null)  localStorage.setItem('selectedPattern', this.selectedPattern);
    if(this.selectedDecorator != null)localStorage.setItem('selectedDecorator', this.selectedDecorator);
  }
}
