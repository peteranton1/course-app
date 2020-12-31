yieldUnescaped '<!DOCTYPE html>'
html {
    head {
        title("$pageTitle $breadcrumbs")
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
        link(rel: 'stylesheet', href: '/css/bootstrap.min.css')
    }
    body(class: 'body') {
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
            table(class: 'table') {
                tr {
                    td {
                        yield message ?: ''
                    }
                }
            }
            table(class: 'table') {
                tr {
                    td {
                        mainBody()
                    }
                }
            }
            table(class: 'table') {
                tr {
                    td {
                        yield ''
                    }
                }
            }
        }
    }
}
