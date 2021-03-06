/*
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { Component, OnInit } from '@angular/core';
import { DialogService } from "../services/dialog.service";
import { SnackbarService } from "../services/snackbar.service";
import { ActivatedRoute } from "@angular/router";
import { ClientService } from "../services/client.service";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss']
})
export class ClientsComponent implements OnInit {
  private clients: any[];
  domainId: string;
  newClientRouterLink:any[] = ['/clients', 'new'];

  constructor(private dialogService: DialogService,
              private snackbarService: SnackbarService, private clientService: ClientService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.domainId = this.route.snapshot.parent.params['domainId'];
    this.clients = this.route.snapshot.data['clients'];
    if (this.domainId) {
      this.newClientRouterLink.push({'domain': this.domainId});
    }
  }

  get isEmpty() {
    return !this.clients || this.clients.length == 0;
  }
}
