yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        title(pageTitle)
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        link(rel: 'stylesheet', href: '/css/bootstrap.min.css')
    }
    body {
        div(class: 'container') {
            div(class: 'navbar') {
                div(class: 'navbar-inner') {
                    a(class: 'brand',
                            href: '/') {
                        yield 'Topics, Courses and Lessons '
                    }
                    var href = breadcrumbs?.join("/")
                    a(class: 'brand',
                        href: "$href") {
                        yield "$href"
                    }
                }
            }
            mainBody()
        }
    }
}
