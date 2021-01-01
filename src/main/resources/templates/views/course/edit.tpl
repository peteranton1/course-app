layout 'layouts/main.tpl',
        pageTitle: 'Topics, Courses, Lessons - ',
        breadcrumbs:  breadcrumbs ?: '',
        message: message ?: '',
        mainBody: contents {
            boolean exists = !"add".equals(courseId)
            div(class: article){
                form (id:'editForm', action: breadcrumbs.join("/")+"/update", method:'post') {
                    if(exists){
                        input(name: 'id', type: 'hidden', value: course.id ?: '')
                    }
                    label(for: 'Name', 'Name')
                    input(name: 'name', type: 'text', value: course.name ?: newId)
                    label(for: 'Description', 'Description')
                    input(name: 'description', type: 'text', value: course.description ?: newId)
                    div(class: 'form-actions') {
                        input(type: 'submit', value: 'Submit')
                    }
                }
            }
            div(class: article){
                if(exists){
                    div("Maintain the lists of lessons.")
                    a(class: 'brand',
                            href: "/topic/$topicId/course/$course.id/lesson") {
                        yield "See Lessons for Course $course.id"
                    }
                    div("&nbsp;")
                    div("Remove Course $course.name: $course.description (only if all lessons removed first)")
                    a(class: 'brand',
                            href: "/topic/$topicId/course/$course.id/delete") {
                        yield "Delete Course $course.name: $course.description"
                    }
                }
            }
        }