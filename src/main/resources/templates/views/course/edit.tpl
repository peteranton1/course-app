layout 'layouts/main.tpl',
        breadcrumbs:  breadcrumbs,
        pageTitle: 'Edit Topic',
        mainBody: contents {
            form (id:'editForm', action: breadcrumbs.join("/"), method:'post') {
                input(name: 'id', type: 'hidden', value: topic.id ?: '')
                label(for: 'Name', 'Name')
                input(name: 'name', type: 'text', value: topic.name ?: '')
                label(for: 'Description', 'Description')
                input(name: 'description', type: 'text', value: topic.description ?: '')
                div(class: 'form-actions') {
                    input(type: 'submit', value: 'Submit')
                }
            }
        }