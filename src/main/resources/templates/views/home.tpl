layout 'layouts/main.tpl',
        breadcrumbs:  breadcrumbs,
        pageTitle: 'Spring Boot - Groovy templates example with layout',
        mainBody: contents {
            div(class: article){
                div("Maintain the lists of topics, courses and lessons.")
                a(class: 'brand',
                        href: '/topic') {
                    yield 'See Topics, Courses and Lessons '
                }
            }
        }
