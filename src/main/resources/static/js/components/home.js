'use strict';

app.component('home', {
    controller: function (RestAPI, $scope, $timeout) {
        RestAPI.get("/clients")
            .then(function (response) {
                $scope.clientInfo = response.data;
            }, function (reason) {
                $scope.error = reason.data
            });

        $scope.handleClient = function (client) {
            $scope.checkout_client = client
        }

        $scope.searchClient = function (search_first_name, search_last_name) {
            if (isUndefined(search_first_name) && isUndefined(search_last_name)) {
                return;
            }
            let first_name = isUndefined(search_first_name) ? "" : search_first_name;
            let last_name = isUndefined(search_last_name) ? "" : search_last_name;
            RestAPI.get("/clients?firstName=" + first_name + "&lastName=" + last_name)
                .then(function (response) {
                    $scope.clientInfo = response.data;
                    $scope.search_first_name = ""
                    $scope.search_last_name = ""
                }, function (reason) {
                    $scope.error = reason.data
                });
        }

        $scope.createClient = function (new_client_fn, new_client_ln, new_client_phone, new_client_db) {
            if (isUndefined(new_client_fn)) {
                return;
            }
            if (isUndefined(new_client_ln)) {
                return;
            }
            if (isUndefined(new_client_phone)) {
                return;
            }
            if (isUndefined(new_client_db)) {
                return;
            }
            let client =
                {
                    "firstName": new_client_fn,
                    "lastName": new_client_ln,
                    "date": new_client_db,
                    "phone": new_client_phone
                };

            RestAPI.post("/clients", client)
                .then(function (response) {
                    $scope.new_client_fn = ""
                    $scope.new_client_ln = ""
                    $scope.new_client_db = ""
                    $scope.new_client_phone = ""
                    $scope.clientInfo = response.data;
                    showSuccess("Successful checkout!")
                }, function (reason) {
                    $scope.error = reason.data
                });
        }

        $scope.handleCheckout = function (checkout_client, checkout_date, checkout_price) {
            if (isUndefined(checkout_client)) {
                showWarning("Select client to checkout an item")
                showHint()
                return;
            }

            if (isUndefined(checkout_date)) {
                showWarning("Fill in month for checkout")
                return;
            }

            if (isUndefined(checkout_price)) {
                showWarning("Fill in price for checkout")
                return;
            }

            let checkout =
                {
                    "clientId": checkout_client.id,
                    "date": checkout_date,
                    "price": checkout_price
                };

            RestAPI.post("/reports", checkout)
                .then(function (response) {
                    $scope.checkout_client = ""
                    $scope.checkout_date = ""
                    $scope.checkout_price = ""
                    showSuccess("Successful checkout!")
                }, function (reason) {
                    $scope.error = reason.data
                });
        }

        function showWarning(message) {
            $scope.warning = message;
            $timeout(function () {
                $scope.warning = "";
            }, 1000);
        }

        function showSuccess(message) {
            $scope.success = message;
            $timeout(function () {
                $scope.success = "";
            }, 1000);
        }

        function showHint() {
            $scope.hint = true;
            $timeout(function () {
                $scope.hint = false;
            }, 1000);
        }

        function isUndefined(val) {
            return val === undefined || val === '';
        }
    },
    template:
        `<div class="container">
   <div class="row justify-content-md-center" style="padding-top: 30px;">
      <div class="col-lg-12" ng-show="success" style="margin: 5px">
         <div class="row">
            <div class="col-lg-12 justify-content-center">
               <div class="alert alert-success" role="alert">
                  {{ success }} 
               </div>
            </div>
         </div>
      </div>
      <div class="col-lg-7">
         <div class="row" style="padding: 20px; background: #eceeef; border-radius: 10px; margin: 5px">
            <div class="col-lg-12" >
               <div class="row">
                  <div class="col-lg-12">
                     <h5>Client</h5>
                  </div>
                  <div class="col-lg-12">
                     <div class="row" style="padding-bottom: 30px; padding-top: 20px">
                        <div class="col-lg-3"><input class="form-control" ng-model="search_first_name" type="text" placeholder="First Name"></div>
                        <div class="col-lg-3"><input class="form-control" ng-model="search_last_name" type="text" placeholder="Last Name"></div>
                        <div class="col-lg-2"><button type="button" ng-click="searchClient(search_first_name, search_last_name)" class="btn btn-secondary">Search</button></div>
                        <div class="col-lg-2"><button type="button" ng-click="createClient()" 
                           data-toggle="modal" 
                           data-target="#newClientModal"
                           class="btn btn-primary">New Client</button></div>
                        <div class="col-lg-2"></div>
                     </div>
                  </div>
                  <div class="col-lg-12">
                     <table class="table table-hover" ng-style="hint && {'background-color':'#fff3cd'}">
                        <thead class="thead-dark">
                           <tr>
                              <th scope="col">First Name</th>
                              <th scope="col">Last Name</th>
                              <th scope="col">Phone</th>
                              <th scope="col">Date of Birth</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr ng-repeat="client in clientInfo.clients"  ng-click="handleClient(client)">
                              <td>{{ client.firstName }}</td>
                              <td>{{ client.lastName }}</td>
                              <td>{{ client.phone }}</td>
                              <td>{{ client.date }}</td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
<!--      New feature            -->
<!--                  <div class="col-lg-12">-->
<!--                     <nav aria-label="...">-->
<!--                        <ul class="pagination pagination-sm justify-content-center">-->
<!--                           <li class="page-item active" aria-current="page">-->
<!--                              <span class="page-link">1</span>-->
<!--                           </li>-->
<!--                           <li class="page-item"><a class="page-link" href="#">2</a></li>-->
<!--                           <li class="page-item"><a class="page-link" href="#">3</a></li>-->
<!--                        </ul>-->
<!--                     </nav>-->
<!--                  </div>-->
               </div>
            </div>
         </div>
      </div>
      <div class="col-lg-5" >
         <div class="row" style="padding: 20px; background: #eceeef; border-radius: 10px; margin: 5px">
            <div class="col-lg-12">
               <h5>Сheckout</h5>
            </div>
            <div class="col-lg-12">
               <div class="row" ng-model="checkout_client" ng-show="checkout_client" style="padding-top: 20px;padding-bottom: 20px" >
                  <div class="col-lg-12">Client: <b>{{ checkout_client.firstName }} {{ checkout_client.lastName }}</b></div>
               </div>
            </div>
            <div class="col-lg-12">
               <div class="row">
                  <div class="col-lg-10">
                     <div class="input-group mb-3">       
                        <span class="input-group-text" id="basic-addon1">Month</span>                 
                        <input type="month" ng-model="checkout_date" class="form-control" aria-describedby="basic-addon1">
                     </div>
                  </div>
               </div>
            </div>
            <div class="col-lg-12">
               <div class="row">
                  <div class="col-lg-10">
                     <div class="input-group mb-3">
                        <span class="input-group-text">Price</span>
                        <input type="number" ng-model="checkout_price" class="form-control" aria-label="Amount (to the nearest dollar)">
                     </div>
                  </div>
               </div>
            </div>
            <div class="col-lg-12">
               <div class="row">
                  <div class="col-lg-12 justify-content-center">
                     <button type="button" ng-click="handleCheckout(checkout_client, checkout_date, checkout_price)" class="btn btn-success">Registration</button>
                  </div>
               </div>
            </div>
            <div class="col-lg-12" ng-show="warning" style="padding-top: 20px">
               <div class="row">
                  <div class="col-lg-12 justify-content-center">
                     <div class="alert alert-warning" role="alert">
                        {{warning}}
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
</div>
<div ng-show="error" class="container">
   <div class="alert alert-danger" role="alert">
      <p><b>Error:</b> {{error.error}}</p>
      <p><b>Message:</b> {{ error.message }} </p>
   </div>
</div>
<!-- Modal -->
<div class="modal fade" id="newClientModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
   <div class="modal-dialog" role="document">
      <div class="modal-content">
         <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Create new client</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
            </button>
         </div>
         <div class="modal-body">
            <form>
               <div class="mb-3">
                  <label for="inputFirstName" class="form-label">First Name</label>
                  <input type="text" ng-model="new_client_fn" class="form-control" id="inputFirstName">
                  <span ng-hide="new_client_fn" style="padding-top: 20px; color: red">* required field</span>
               </div>
               <div class="mb-3">
                  <label for="inputLastName" class="form-label">Last Name</label>
                  <input type="text" ng-model="new_client_ln" class="form-control" id="inputLastName">
                  <span ng-hide="new_client_ln" style="padding-top: 20px; color: red">* required field</span>
               </div>
               <div class="mb-3">
                  <label for="inputPhone" class="form-label">Phone</label>
                  <input type="text" ng-model="new_client_phone" class="form-control" id="inputPhone">
                  <span ng-hide="new_client_phone" style="padding-top: 20px; color: red">* required field</span>
               </div>
               <div class="mb-3">
                  <label for="inputDb" class="form-label">Date of Birth</label>
                  <input type="date" ng-model="new_client_db" class="form-control"  id="inputDb" aria-describedby="basic-addon1">
                  <span ng-hide="new_client_db" style="padding-top: 20px; color: red">* required field</span>
               </div>
            </form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" 
               ng-click="createClient(new_client_fn, new_client_ln, new_client_phone, new_client_db)" 
               ng-attr-data-dismiss="{{ new_client_fn && new_client_ln && new_client_phone && new_client_db ? 'modal' : '' }}" class="btn btn-primary">Save</button>
         </div>
      </div>
   </div>
</div>`
});