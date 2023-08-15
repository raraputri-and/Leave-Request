$(document).ready(function () {
    $('#table-joint-leave').DataTable({
        ajax: {
            url: '/api/joint-leave',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'name'},
            { data: 'date',
                title: 'Date',
                render: function (data, type, row) {
                    var date = new Date(data);
                    var options = { day: '2-digit', month: '2-digit', year: 'numeric' };
                    return date.toLocaleDateString('en-GB', options).split('/').join('-');
                }},
            { data: 'isHoliday'},
            {
                "data": null,
                render: function (data, type, row, meta) {
                    return `<div class="d-flex">
                        <button data-bs-toggle="modal" data-bs-target="#updateJointLeave" onclick="beforeUpdate('${row.id}')"
                            class="btn btn-warning px-5 py-2 me-3">Edit</button>
                        <button onclick="deleteJointLeave(${row.id})" type="submit" 
                        class="btn btn-danger px-5 py-2 me-3">Delete</button>
                    </div>`
                }
            }
        ]
    });
});

function createJointLeave() {
    let nameVal = $("#name").val()
    let dateVal = $("#date").val()
    let isHolidayVal = $("#isHoliday").val()
    $.ajax({
        method: "POST",
        url: "/api/joint-leave",
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        data: JSON.stringify({
            name: nameVal,
            date: dateVal,
            isHoliday: isHolidayVal
        }),
        contentType: "application/json",
        success: function (result) {
            $("#createJointLeave").modal('hide')
            $("#table-joint-leave").DataTable().ajax.reload()
            $("#name").val('')
            $("#date").val('')
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Joint Leave has been created',
                showConfirmButton: false,
                timer: 2000
            })
        }
    })
}

function beforeUpdate(id){
    $.ajax({
            method: "GET",
            url: "/api/joint-leave/" + id,
            dataType: "JSON",
            beforeSend: addCsrfToken(),
            success: (result) => {
                $("#idUpdate").val(result.id)
                $("#name2").val(result.name)
                $("#date2").val(result.date)
                $("#isHoliday2").val(result.isHoliday)
            }
        }
    )
}

function updateJointLeave() {
    let idVal = $("#idUpdate").val()
    let nameVal = $("#name2").val()
    let dateVal = $("#date2").val()
    let isHolidayVal = $("#isHoliday2").val()
    Swal.fire({
        title: 'Are you sure?',
        text: "You want to update this joint-leave?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, update it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: "/api/joint-leave/update/" + idVal,
                method: "PUT",
                dataType: "JSON",
                beforeSend: addCsrfToken(),
                data: JSON.stringify({
                    name: nameVal,
                    date: dateVal,
                    isHoliday: isHolidayVal
                }),
                contentType: "application/json",
                success: (result) => {
                    $("#table-joint-leave").DataTable().ajax.reload()
                    $("#updateJointLeave").modal('hide')
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'JointLeave has been updated',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            })
        }
    })
}

function deleteJointLeave(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: "/api/joint-leave/" + id,
                method: "DELETE",
                dataType: "JSON",
                beforeSend: addCsrfToken(),
                success: (result) => {
                    $("#table-joint-leave").DataTable().ajax.reload()
                    Swal.fire({
                        title: 'JointLeave has been deleted.',
                        icon: 'success',
                        width: 600,
                        color: 'black',
                        background: '#fff',
                        showConfirmButton: false,
                        timer: 2000
                    })
                }
            })
        }
    })
}