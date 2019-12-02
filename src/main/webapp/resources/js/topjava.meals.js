$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
                        "desc"
                    ]
                ]
            })
        }
    );
});

$('#dateTime').datetimepicker(
    {
        format: 'Y-m-d\\TH:i',
    }
);

function applyFilter() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl + "filter",
        data: $('#filter').serialize()
    }).done(
        function (data) {
            context.datatableApi.clear().rows.add(data).draw();
            successNoty("Filtered");
        });
}

function clearFilter() {
    updateTable();
}