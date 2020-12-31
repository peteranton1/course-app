layout 'layouts/main.tpl',
        pageTitle: 'Topics, Courses, Lessons - ',
        breadcrumbs:  breadcrumbs ?: '',
        message: message ?: '',
        mainBody: contents {
            ul {
                topics.each { topic ->
                    li {
                        a(href:"/topic/$topic.id", "$topic.name : $topic.description")
                    }
                }
            }

            div {
                a(href:'/topic/add', 'Add new topic')
            }
        }
