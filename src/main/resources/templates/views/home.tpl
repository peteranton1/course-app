layout 'layouts/main.tpl',
        pageTitle: 'Topics, Courses, Lessons - ',
        breadcrumbs:  breadcrumbs ?: '',
        message: message ?: '',
        mainBody: contents {
            div(class: article){
                div("Maintain the lists of topics, courses and lessons.")
                a(class: 'brand',
                        href: '/topic') {
                    yield 'See Topics, Courses and Lessons '
                }
            }
        }
