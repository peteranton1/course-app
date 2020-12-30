layout 'layouts/main.tpl',
        breadcrumbs:  breadcrumbs,
        pageTitle: 'Edit Topic',
        mainBody: contents {
            boolean exists = topic.id && !"add".equals(topic.id)
            div(class: article){
                form (id:'editForm', action: breadcrumbs.join("/"), method:'post') {
                    if(exists){
                        input(name: 'id', type: 'hidden', value: topic.id ?: '')
                    } else {
                        label(for: 'Id', 'Id')
                        input(name: 'id', type: 'text', value: newId)
                    }
                    label(for: 'Name', 'Name')
                    input(name: 'name', type: 'text', value: topic.name ?: newId)
                    label(for: 'Description', 'Description')
                    input(name: 'description', type: 'text', value: topic.description ?: newId)
                    div(class: 'form-actions') {
                        input(type: 'submit', value: 'Submit')
                    }
                }
            }
            div(class: article){
                if(exists){
                    div("Maintain the lists of courses and lessons.")
                    a(class: 'brand',
                            href: "/topic/$topic.id/course") {
                        yield "See Courses for Topic $topic.id"
                    }
                }
            }
        }