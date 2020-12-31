layout 'layouts/main.tpl',
        breadcrumbs:  breadcrumbs,
        pageTitle: 'List Lessons',
        topicId: topicId,
        courseId: courseId,
        mainBody: contents {
            ul {
                lessons.each { lesson ->
                    li {
                        a(href:"/topic/$topicId/course/$courseId/lesson/$lesson.id", "$lesson.name : $lesson.description")
                    }
                }
            }

            div {
                a(href:"/topic/$topicId/course/$courseId/lesson/add", 'Add new lesson')
            }
        }
