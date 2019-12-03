$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

$(document).ready(function(){
    $("input:checkbox").change(function() {
        if($(this).is(":checked")) {
            $.ajax({
                url: context.ajaxUrl+"enabled",
                type: 'GET',
                data: { userId:$(this).closest('tr').attr('id'), state:"1" }
            }).done(function () {
                updateTable();
                successNoty("Enabled!");
            });
        } else {
            $.ajax({
                url: context.ajaxUrl+"enabled",
                type: 'GET',
                data: { userId:$(this).closest('tr').attr('id'), state:"0" }
            }).done(function () {
                updateTable();
                successNoty("Disabled!");
            });
        }
    });
});