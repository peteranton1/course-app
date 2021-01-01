layout 'layouts/main.tpl',
        pageTitle: 'Topics, Courses, Lessons - ',
        breadcrumbs:  breadcrumbs ?: '',
        message: message ?: '',
        mainBody: contents {
            boolean exists = !"add".equals(lessonId)
            div(class: article){
                form (id:'editForm', action: breadcrumbs.join("/")+"/update", method:'post') {
                    if(exists){
                        input(name: 'id', type: 'hidden', value: lesson.id ?: '')
                    }
                    label(for: 'Name', 'Name')
                    input(name: 'name', type: 'text', value: lesson.name ?: newId)
                    label(for: 'Description', 'Description')
                    input(name: 'description', type: 'text', value: lesson.description ?: newId)
                    div(class: 'form-actions') {
                        input(type: 'submit', value: 'Submit')
                    }
                }
            }
            div(class: article){
                if(exists){
                    div("&nbsp;")
                    div("Remove Lesson $lesson.name: $lesson.description")
                    a(class: 'brand',
                            href: "/topic/$topicId/course/$courseId/lesson/$lesson.id/delete") {
                        yield "Delete Lesson $lesson.name: $lesson.description"
                    }
                }
            }
        }