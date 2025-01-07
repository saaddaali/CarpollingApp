import {Component, OnInit} from '@angular/core';
import gsap from 'gsap';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'app-contact-us',
    templateUrl: './contact-us.component.html',
    styleUrls: ['./contact-us.component.scss']
})
export class ContactUsComponent implements OnInit {
    contactForm: FormGroup;

    constructor(private fb: FormBuilder) {
        this.contactForm = this.fb.group({
            name: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            subject: ['', Validators.required],
            message: ['', Validators.required]
        });
    }

    ngOnInit(): void {}

    onSubmit(): void {
        if (this.contactForm.valid) {
            console.log(this.contactForm.value);
            // Handle form submission
        }
    }
}
