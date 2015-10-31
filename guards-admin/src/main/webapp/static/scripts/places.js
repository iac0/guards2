$(document).ready(function(){
    var $select2Guard = $("#guardsSelect");
    $('#external-events .fc-event').each(function() {

        // store data so the calendar knows to render an event upon drop
        $(this).data('event', {
            title: $(this).html(),
             id: $(this).data("id")//.find("span.placeName").text(),
          //  start: moment($(this).data("start")),
          //  end: moment($(this).data("end"))
        });

        // make the event draggable using jQuery UI
        $(this).draggable({
            zIndex: 999,
            revert: true,
             // will cause the event to go back to its
            revertDuration: 0  //  original position after the drag
        });

    });
    var $guardCalendar = $('#calendar').fullCalendar({
        //events: {
        //    url: window.BASE+'calendar/fetch',
        //    method: 'POST',
        //    data: function() { // a function that returns an object
        //        return {
        //            guard_id:$select2Guard.val()
        //        }
        //
        //    }
        //},
        events: function(start, end, timezone, callback) {
            $.ajax({
                url: window.BASE+'calendar/fetch',
                dataType: 'json',
                method: 'POST',
                data: {
                    // our hypothetical feed requires UNIX timestamps
                    guard_id:$select2Guard.val() ,
                    start: start.format(),
                    end: end.format()
                },
                success: function(doc) {
                    var events = [];

                    callback(doc.value);
                }
            });
        },
        displayEventEnd: true,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,basicWeek,agendaDay'
        },
        lang:"it",
        timezone: 'Europe/Rome',
        allDaySlot: false,
        defaultView: "basicWeek",
        editable: true,
        eventOrder:"guardid",
        droppable: true,
        dropAccept: function(){
            if ( $("#guardsSelect").val().length == 0 ){
                bootbox.alert("Seleziona una guardia prima di completare il calendario");
                return false;
            }
            return true;
        }, // this allows things to be dropped onto the calendar
        eventReceive: function(event) {
            var url = window.BASE+'calendar/create';
            $.post(url,{
                placeId: event.id,
                startDate: event.start.format("YYYYMMDDHH:mm"),
                guardId: $select2Guard.val()
            })
                .success(function(data){
                      if ( data.error){
                           bootbox.alert(data.message);
                      }
                      event.id = data.value.id;
                      event.placeid  = data.value.place.id;
                      event.guardid =  data.value.guard.id;
                })
                .error(function(data){
                    bootbox.alert("Il server non è in funzione")
                });

        } ,
        eventDrop: function(event){
            var url = window.BASE+'calendar/update';
            $.post(url,{
                eventId: event.id,
                startDate: event.start.format("YYYYMMDDHH:mm")
            }).success(function(data){
                if ( data.error){
                    bootbox.alert(data.message);
                }
            })
                .error(function(data){
                    bootbox.alert("Il server non è in funzione")
                });
        },
        eventRender: function(event, element){
            element.find(".fc-event-title").remove();
            element.find(".fc-event-time").remove();
            element.html("<h5 class='title text-center'>"+event.title+"<br></h5>");
            var currentElement = $("<span class=\"glyphicon glyphicon-trash trash\" data-eventid='"+event.id+"' style='margin-top:5px; '></span>");
            element.find(".title").append($(currentElement));
            $(currentElement).on("click",function(){
                var eventId = $(this).data("eventid");
                bootbox.confirm("Sei sicuro di voler eliminare il turno?", function(result) {
                    if ( result === true ) {
                        $.post(window.BASE+'calendar/remove',{eventId:eventId})
                            .success(function(){
                                $guardCalendar.fullCalendar("removeEvents",eventId);
                        })
                    }
                });
            })

        }
    });


    $('#searchplaces').on("keyup",function(){
     var text = $(this).val().toLowerCase();
        text = text.split(' ').join("");
        if ( !text ){
            $(".fc-event.place").hide();
            $(".fc-event.place").slice(0,10).show();
            return;
        }
        $(".fc-event.place").hide();
        var selected = $(".fc-event.place").filter("[data-name*="+text+"]");
        $(selected).each(function( index ) {
            //if ( index >= 10 ){
            //    return;
            //}   else {
                $(this).show();
            //}
        })  ;

    }) ;



    $select2Guard.select2({
        placeholder: "Seleziona una guardia per caricare il suo calendario",
        allowClear: true
    });
    $select2Guard.on("change", function (e) {
    //refetch degli eventi
        $guardCalendar.fullCalendar( 'refetchEvents' )
    });
});
