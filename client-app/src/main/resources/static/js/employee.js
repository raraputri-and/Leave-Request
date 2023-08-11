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
    religionSelector()
    function religionSelector() {
        $.ajax({
            url: "/api/religion",
            method: "GET",
            dataType: "JSON",
            success: function (data) {
                let selectReligion = $(".religion-selector");

                $.each(data, function (index, religion) {
                    selectReligion.append(`<option value="${religion.id}"> ${religion.name} </option>`)
                })
            }
        })
    }
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
                $("#religion").val(result.religion)
                // $("#manager").val(result.manager.id)
                $("#username").val(result.user.username)
            }
        }
    )
}