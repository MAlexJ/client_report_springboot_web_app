'use strict';

app.component('report', {
    controller: function (RestAPI, $scope) {
        RestAPI.get("/reports")
            .then(function (response) {
                $scope.reports = response.data;
            }, function (reason) {
                $scope.error = reason.data
            });

        $scope.findBuyerByMoth = function (month, buyers) {
            let result = [];
            if (!buyers) {
                result.push('none')
                return result;
            }

            buyers.forEach(buyer => {
                let price = buyer[month.toLowerCase()]
                if (price) {
                    buyer.price = '$'.concat(price)
                    result.push(buyer);
                }
            });
            if (result.length === 0) {
                result.push('none')
            }
            return result.sort((a, b) => (a.fullName > b.fullName) ? 1 : ((b.fullName > a.fullName) ? -1 : 0));
        }
    },
    template:
        `<div class="container">
   <div class="row justify-content-md-center" style="padding-top: 10px; display: block">
      <div class="row" style="padding: 20px;">
         <div class="col-lg-12" style="padding: 20px; background: #eceeef; border-radius: 10px; margin-bottom: 10px">
            <div class="row">
               <div class="col-lg-6">
                  <div class="row" >
                     <div class="col-lg-12" style="padding-top: 10px; padding-bottom: 10px">
                        <h6>Active buyers:</h6>
                     </div>
                     <div class="col-lg-12" ng-repeat="reportMonth in reports.reportMonths">
                        <div class="row">
                           <div class="col-lg-12" style="color: #3498db; margin-top: 10px; font-style: italic">{{ reportMonth }}</div>
                           <div class="col-lg-12" 
                              ng-repeat="buyer in findBuyerByMoth(reportMonth, reports.activeBuyers)"
                              style="padding-left: 50px">
                              <span ng-show="buyer.fullName">- {{ buyer.fullName }} ({{ buyer.phone }}) : {{buyer.price}} </span>
                              <span ng-hide="buyer.fullName">{{ buyer }} </span>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
               <div class="col-lg-6">
                  <div class="row">
                     <div class="col-lg-12" style="padding-top: 20px; padding-bottom: 10px">
                        <h6>Inactive buyers:</h6>
                     </div>
                     <div class="col-lg-12" ng-repeat="reportMonth in reports.reportMonths">
                        <div class="row">
                           <div class="col-lg-12" style="color: #3498db; margin-top: 10px; font-style: italic">{{ reportMonth }}</div>
                           <div class="col-lg-12" 
                              ng-repeat="buyer in findBuyerByMoth(reportMonth, reports.inactiveBuyers)"
                              style="padding-left: 50px">
                              <span ng-show="buyer.fullName">- {{ buyer.fullName }} ({{ buyer.phone }}) : {{buyer.price}} </span>
                              <span ng-hide="buyer.fullName">{{ buyer }} </span>
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
</div>`
});
