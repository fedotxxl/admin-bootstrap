angular.module("app")
    .factory("_filterItem", function (_i18n) {
       var answer = {
           toUniqueId: function (item) {
               return item.type + "_" + item.id;
           },
           uniqueIdToObject: function (uniqueId) {
               var pos = uniqueId.lastIndexOf("_");
               var id = uniqueId.substring(pos + 1);

               if (_.isNumeric(parseInt(id))) {
                   id = parseInt(id);
               }

               return {
                   type: uniqueId.substring(0, pos),
                   id: id
               }
           },
           postProcess: function (item) {
                var typeTranslation = _i18n("filter.item." + item.type);

                item.idUnique = answer.toUniqueId(item);
                item.text = typeTranslation + " " + item.label;
                item.typeTranslation = typeTranslation;

                return item;
           },
           getDataAndPostProcess: function(promise) {
               return function() {
                   return promise.apply(this, arguments).then(function (response) {
                       return _.map(response.data, answer.postProcess);
                   })
               }
           }
       };

       return answer;
    });