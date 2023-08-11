$(document).ready(function () {
    $('#table-user').DataTable({
        ajax: {
            url: '/api/emp',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'nip'},
            { data: 'name'},
            { data: 'gender'},
            { data: 'religion.name'},
            { data: 'manager'},
            { data: 'user.username'},
            {
                "data": null,
                render: function (data, type, row, meta) {
                    return `<div class="d-flex">
                        <button data-bs-toggle="modal" data-bs-target="#updateEmployee" onclick="beforeUpdate('${row.id}')"
                            class="btn btn-warning px-5 py-2 me-3">Edit</button>
                    </div>`
                }
            }
        ]
    });
});

function beforeUpdate(id){
    $.ajax({
            method: "GET",
            url: "/api/emp/" + id,
            dataType: "JSON",
            beforeSend: addCsrfToken(),
            success: (result) => {
                $("#nip").val(result.nip)
                $("#name").val(result.name)
                $("#gender").val(result.gender)
                $("#religion").val(result.religion.name)
                // $("#manager").val(result.manager.id)
                $("#username").val(result.user.username)
            }
        }
    )
}