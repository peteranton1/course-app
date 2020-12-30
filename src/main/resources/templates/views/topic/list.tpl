layout 'layouts/main.tpl',
        breadcrumbs:  breadcrumbs,
        pageTitle: 'List Topics',
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
