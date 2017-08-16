var vRoutes = {
    auth: {
        login: "/auth/login.html"
    },
    app: {
        root: "/app/",
        settings: {
            users: {
                list: "/app/settings/users/",
                item: {
                    create: "/app/settings/users/new",
                    edit: "/app/settings/users/:id"
                }
            }
        }
    }
};