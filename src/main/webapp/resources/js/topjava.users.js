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

$(document).ready(function () {
    $("input:checkbox").change(function () {
        var isActive;
        var noty;
        if ($(this).is(":checked")) {
            isActive = true;
            noty = "Enabled!";
        } else {
            isActive = false;
            noty = "Disabled!";
        }

        $.ajax({
            url: context.ajaxUrl + "enabled",
            type: 'GET',
            data: {userId: $(this).closest('tr').attr('id'), state: isActive}
        }).done(function () {
            updateTable();
            successNoty(noty);
        });

    });
});