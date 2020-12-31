layout 'layouts/main.tpl',
        breadcrumbs:  breadcrumbs,
        pageTitle: 'List Courses',
        topicId: topicId,
        mainBody: contents {
            ul {
                courses.each { course ->
                    li {
                        a(href:"/topic/$topicId/course/$course.id", "$course.name : $course.description")
                    }
                }
            }

            div {
                a(href:"/topic/$topicId/course/add", 'Add new course')
            }
        }
