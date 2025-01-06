import {Component, OnInit} from '@angular/core';
interface ChartData {
    name: string;
    amount: number;
}

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

    monthlyData: ChartData[] = [
        {name: 'January', amount: 65},
        {name: 'February', amount: 59},
        {name: 'March', amount: 80},
        {name: 'April', amount: 81},
        {name: 'May', amount: 56},
        {name: 'June', amount: 55},
        {name: 'July', amount: 40}
    ];

    basicData: any;
    basicOptions: any;

    ngOnInit() {
        this.initChart();
    }

    private initChart() {
        this.basicData = {
            labels: this.monthlyData.map(d => d.name),
            datasets: [
                {
                    label: 'Revenue',
                    data: this.monthlyData.map(d => d.amount),
                    fill: false,
                    borderColor: '#3B82F6',
                    tension: 0.4
                }
            ]
        };

        this.basicOptions = {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                }
            }
        };
    }

}
